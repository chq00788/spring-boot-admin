package com.chq.project.admin.system.service;

import com.chq.project.admin.system.dao.UserDao;
import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.RoleModel;
import com.chq.project.admin.system.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 描述：用户管理 服务实现层
 *
 * @author CHQ
 * @date 2019-01-19
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 查询数据
     *
     * @return
     */
    public List<UserModel> selectList(Map<String, Object> searchMap) {
        return userDao.selectList(searchMap);
    }


    /**
     * 新增数据
     *
     * @param model
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(UserModel model) {
        model.setIsDelete(0);
        model.setSalt("123456");
        model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
        userDao.insert(model);
    }

    /**
     * 更新数据
     *
     * @param model
     */
    public void update(UserModel model) {
        userDao.update(model);
    }

    /**
     * 删除数据(逻辑删除)
     *
     * @param id
     */
    public void delete(Integer id) {
        UserModel user = getById(id);
        user.setIsDelete(1);
        userDao.update(user);
    }

    /**
     * 根据ID查询数据
     *
     * @param id
     */
    public UserModel getById(Integer id) {
        return userDao.getById(id);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public UserModel getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    /**
     * 保存用户角色信息
     *
     * @param userId
     * @param roleIds
     */
    public void saveUserRole(Integer userId, Integer[] roleIds) {
        //删除原来的角色信息
        userDao.deleteUserRole(userId);
        //保存新的角色信息
        for (int i = 0; i < roleIds.length; i++) {
            userDao.saveUserRole(userId, roleIds[i]);
        }
    }

    /**
     * 根据用户名查询用户菜单权限
     *
     * @param username
     * @return
     */
    public List<PermissionModel> getMenusByUsername(String username) {
        UserModel user = userDao.getMenusByUsername(username);
        List<PermissionModel> roots = new ArrayList<>();
        HashSet<PermissionModel> perms = new HashSet<>();
        if (null != user) {
            for (RoleModel role : user.getRoleList()) {
                perms.addAll(role.getPermissionList());
            }
            List<PermissionModel> menus = new ArrayList<>(perms);

            for (PermissionModel model : menus) {
                if (Integer.valueOf(0).equals(model.getPermPid())) {
                    model.setChildren(createMenus(menus, model.getId()));
                    roots.add(model);
                }
            }
        }
        return roots;
    }

    private List<PermissionModel> createMenus(List<PermissionModel> menuList, Integer pid) {
        List<PermissionModel> menus = new ArrayList<>();
        for (PermissionModel model : menuList) {
            if (pid.equals(model.getPermPid())) {
                menus.add(model);
            }
        }
        return menus;
    }
}