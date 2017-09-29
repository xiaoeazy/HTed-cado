package com.huan.HTed.core.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface IAuthenticationSuccessListener extends Comparable<IAuthenticationSuccessListener> {

    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response);

    default int getOrder() {return 10;}

    @Override
    default int compareTo(IAuthenticationSuccessListener o) {
        return getOrder() - o.getOrder();
    }
}
