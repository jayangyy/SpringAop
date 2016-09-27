/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xdemo.example.springaop.service;

/**
 *
 * @author Jayang
 */
public enum EduReviewsStatus {

    Start(0), Process(1), End(2), Return(3);
    // 成员变量
    private int status;

    // 构造方法
    private EduReviewsStatus(int status) {
        this.status = status;
    }

//        // 普通方法
//        public static String getName(int index) {
//            for (Color c : Color.values()) {
//                if (c.getIndex() == index) {
//                    return c.name;
//                }
//            }
//            return null;
//        }
    // get set 方法
    public int getStatsu() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
