package com.czn.shoppingmall.controller.portal;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.ICartService;
import com.czn.shoppingmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/buyer/cart")
public class CartController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICartService iCartService;

    @RequestMapping("add")
    public ServerResponse add(Integer productId, Integer count, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        if (null == productId || null == count) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCartService.add(productId, count, currentUser.getId());
    }

    @RequestMapping("delete")
    public ServerResponse delete(Integer cartId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        if (null == cartId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCartService.delete(cartId, currentUser.getId());
    }

    @RequestMapping("check")
    public ServerResponse check(Integer cartId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        if (null == cartId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCartService.setCheckStatus(cartId, currentUser.getId(), Const.CartCheck.CART_CHECK);
    }

    @RequestMapping("uncheck")
    public ServerResponse unCheck(Integer cartId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        if (null == cartId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCartService.setCheckStatus(cartId, currentUser.getId(), Const.CartCheck.CART_UNCHECK);
    }

    @RequestMapping("check_all")
    public ServerResponse checkAll(HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        return iCartService.setAllCheckStatus(currentUser.getId(), Const.CartCheck.CART_CHECK);
    }

    @RequestMapping("uncheck_all")
    public ServerResponse unCheckAll(HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_BUYER)) {
            return ServerResponse.createByErrorMessage("您不是买家,请使用买家登陆");
        }
        return iCartService.setAllCheckStatus(currentUser.getId(), Const.CartCheck.CART_UNCHECK);
    }


}
