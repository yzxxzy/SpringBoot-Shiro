package com.chenyi.auth.springbootshiro.shiro;

import org.apache.shiro.authc.DisabledAccountException;

public class NoHaveDeptException extends DisabledAccountException {
    public NoHaveDeptException() {
        super();
    }

    public NoHaveDeptException(String message) {
        super(message);
    }

    public NoHaveDeptException(Throwable cause) {
        super(cause);
    }

    public NoHaveDeptException(String message, Throwable cause) {
        super(message, cause);
    }
}
