package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.GroupInfoDAO;
import com.dao.UserDAO;
import com.dao.UserGroupsDAO;
import com.dao.UserRelationshipDAO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.pojo.GroupInfo;
import com.pojo.User;
import com.pojo.UserGroups;
import com.pojo.UserRelationship;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService extends ServiceImpl<UserDAO, User> {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupInfoDAO groupInfoDAO;

    @Autowired
    private UserGroupsDAO userGroupsDAO;

    @Autowired
    private UserRelationshipDAO userRelationshipDAO;

    //头像文件夹
    @Value("${upload.headPortrait.dir}")
    private String headPortrait;

    //网站域名
    @Value("${web.site}")
    private String webSite;


    //用户身份检查
    public ResponseBean userCheck(String uId, Long time, String enCode) {
        if (userDAO.selectById(uId) == null) {
            return new ResponseBean(401, "该用户不存在", null);
        } else if (!MyMiniUtils.getEncryptString(uId, time).equals(enCode)) {
            return new ResponseBean(402, "账号校检失败", null);
        } else {
            return new ResponseBean(000, "用户身份验证通过", null);
        }
    }

    //验证登陆
    public ResponseBean verificationLogin(String uid, Long time, String enCode) {
        if (MyMiniUtils.getEncryptString(uid, time).equals(enCode)) {
            //超过时间
            if (time < System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7)) {
                return new ResponseBean(301, "超过登陆时间", null);
            }
            //加密字符串正确
            LocalDateTime uLastTime = userDAO.userLastUpdateTime(uid);
            //System.out.println("用户最后一次更新时间：" + MyMiniUtils.timeMillisChangeLocalDateTime(uLastTime));
            if (time < MyMiniUtils.timeMillisChangeLocalDateTime(uLastTime)) {
                //在这段时间内信息被改过，重新获取
                User user = userDAO.selectById(uid);
                System.out.println(user);
                user.setuHeadPortrait("https://" + webSite + headPortrait + user.getuHeadPortrait());
                System.out.println("head:" + user.getuHeadPortrait());
                return new ResponseBean(200, "ok", MyMiniUtils.getEncryptString(Integer.toString(user.getuId()), System.currentTimeMillis()), user);
            } else {
                //没有被改过，则返回304
                return new ResponseBean(300, "用户账号信息未改过", MyMiniUtils.getEncryptString(uid, System.currentTimeMillis()), null);
            }
        } else {
            //加密字符串错误
            return new ResponseBean(304, "加密字符串错误", null);
        }
    }

    //用户修改头像
    public String headPortrait(int uId, MultipartFile file) {
        String newFileName = MyMiniUtils.upLoadFile(file, headPortrait);
        if (newFileName != null) {
            User user = new User();
            user.setuId(uId);
            user.setuHeadPortrait(newFileName);
            user.setuUpdateTime(LocalDateTime.now());
            if (userDAO.updateById(user) == 1) {
                return "https://" + webSite + headPortrait + newFileName;
            }
        }
        return null;
    }

    //用户信息修改
    public ResponseBean updateUser(String uId, String userJson) {
        User user = JSON.parseObject(userJson, User.class);
        user.setuId(Integer.parseInt(uId));
        user.setuUpdateTime(LocalDateTime.now());
        if (userDAO.updateById(user) == 1) {
            User userNew = userDAO.selectById(user.getuId());
            userNew.setuHeadPortrait("https://" + webSite + headPortrait + userNew.getuHeadPortrait());
            return new ResponseBean(200, "修改成功", MyMiniUtils.getEncryptString(String.valueOf(user.getuId()), System.currentTimeMillis()), userNew);
        } else
            return new ResponseBean(301, "修改失败，请重试", null);
    }


    //用户创建一个群
    public ResponseBean insertGroupInfo(String groupInfoJson) {
        GroupInfo groupInfo = JSON.parseObject(groupInfoJson, GroupInfo.class);
        if (groupInfo.getGiNickName() == null) {
            groupInfo.setGiNickName("讨论群" + MyMiniUtils.randomNumber("0123456789", 5));
        }
        int i = 0;//下面循环的标识，如果循环三次重样，则加一位数
        int n = 8;//位数确认
        while (true) {
            String uuid = MyMiniUtils.randomNumber("0123456789", n);
            if (groupInfoDAO.giUuidCheck(uuid) != null && uuid.charAt(0) != '0') {
                i++;
                if (i >= 4) {
                    n++;
                }
                continue;
            }
            groupInfo.setGiUuid(uuid);
            break;
        }
        groupInfo.setGiCreateTime(LocalDateTime.now());
        if (groupInfoDAO.insert(groupInfo) == 1) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("gi_master_id", groupInfo.getGiMasterId());
            queryWrapper.eq("gi_create_time", groupInfo.getGiCreateTime());
            groupInfo = groupInfoDAO.selectOne(queryWrapper);
            userGroupsDAO.insert(new UserGroups(groupInfo.getGiMasterId(), groupInfo.getGiId()));
            return new ResponseBean(200, "创建成功", groupInfo);
        } else {
            return new ResponseBean(403, "创建参数异常", null);
        }
    }

    //用户加入一个群
    public ResponseBean joinGroup(int uId, Integer groupId) {
        if (groupId != null) {
            GroupInfo groupInfo = groupInfoDAO.selectById(groupId);
            if (groupInfo == null) {//判断是否有这个群
                return new ResponseBean(404, "请输入正确的群号", null);
            }
            if (userGroupsDAO.insert(new UserGroups(uId, groupId)) == 1) {
                return new ResponseBean(200, "加入成功", null);
            } else {
                return new ResponseBean(405, "参数错误，加入失败", null);
            }
        } else {
            return new ResponseBean(403, "请输入群号", null);
        }
    }

    //用户关注其他用户
    public ResponseBean userRelationshipChange(Integer uId, Integer himselfUid, Integer follow) {
        if (userDAO.selectById(uId) == null) {
            return new ResponseBean(400, "用户不存在", null);
        }
        if (userDAO.selectById(himselfUid) == null) {
            return new ResponseBean(400, "目标用户不存在", null);
        }
        try {
            if (follow == 0) {
                //关注对象
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("myself_uid", uId);
                queryWrapper.eq("himself_uid", himselfUid);
                if (userRelationshipDAO.selectOne(queryWrapper) != null) {
                    return new ResponseBean(200, "成功添加", null);
                } else {
                    UserRelationship userRelationship = new UserRelationship();
                    userRelationship.setMyselfUid(uId);
                    userRelationship.setHimselfUid(himselfUid);
                    userRelationship.setSum(0);
                    if (userRelationshipDAO.insert(userRelationship) == 1) {
                        return new ResponseBean(200, "添加成功", null);
                    }
                }
            } else if (follow == 1) {
                //取消关注
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("myself_uid", uId);
                queryWrapper.eq("himself_uid", himselfUid);
                if (userRelationshipDAO.selectOne(queryWrapper) != null) {
                    //当数据库中有他们的记录
                    if (userRelationshipDAO.delete(queryWrapper) == 1) {
                        return new ResponseBean(200, "取消关注成功", null);
                    } else {
                        return new ResponseBean(400, "取消关注失败", null);
                    }
                } else {
                    //当数据库中没有他们的记录
                    return new ResponseBean(200, "取消关注成功", null);
                }
            }
            return new ResponseBean(404, "参数异常", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(500, "服务器异常", null);
        }
    }

    //用户id或电话号码查公共信息
    public ResponseBean findUserPublicInfo(Integer uId, String tele) {
        QueryWrapper queryWrapper = new QueryWrapper();
        //公开信息
        queryWrapper.select("u_id", "u_name", "u_sex", "u_birthday", "u_head_portrait", "u_province", "u_city", "u_district", "u_address", "u_volunteer");
        queryWrapper.eq("u_id", uId);
        queryWrapper.or();
        queryWrapper.eq("u_tele", tele);
        List<User> users = userDAO.selectList(queryWrapper);
        if (users != null && users.size() > 0) {
            //表示有符合标准的用户
            return new ResponseBean(200, "查询成功", users);
        } else {
            //没有符合保证的用户
            return new ResponseBean(300, "id没有符合的用户", null);
        }
    }

    //使用字符串，查看是不是昵称或者真实姓名
    public ResponseBean findUserPublicInfo(String name, Integer page) {
        Page<User> p = new Page<>(page, 10);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("u_id", "u_name", "u_sex", "u_birthday", "u_head_portrait", "u_province", "u_city", "u_district", "u_address", "u_volunteer");
        queryWrapper.like("u_name", name);
        queryWrapper.or();
        queryWrapper.like("u_real_name", name);
        IPage<User> iPage = userDAO.selectPage(p, queryWrapper);
        if (iPage.getRecords() != null && iPage.getRecords().size() > 0) {
            //有符合标准的用户
            return new ResponseBean(200, "查询成功", iPage.getRecords());
        } else {
            return new ResponseBean(300, "list为空", null);
        }
    }

    //根据关键字来找用户
    public ResponseBean keyWordSelectUser(String keyword, Integer page) throws Exception {
        //验证是不是id
        int uId = Integer.MAX_VALUE; //2147483647
        if (keyword.length() <= 11) {
            try {
                if (MyMiniUtils.isChinaPhoneLegal(keyword)) {
                    //是电话号码
                    return findUserPublicInfo(uId, keyword);
                }
                //不是电话号码
                uId = Integer.parseInt(keyword);
                return findUserPublicInfo(uId, keyword);
            } catch (Exception e) {
                System.out.println("不是id，也不是电话号码");
            }
        }
        //检查是不是昵称或真实姓名
        return findUserPublicInfo(keyword, page);
    }

}
