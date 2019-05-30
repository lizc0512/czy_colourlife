package com.cashier;

import com.external.eventbus.EventBus;

/*
 *                                                                                                         
 *                  ░▓▓▓▓▓░                                  
 *               ░▓▓▓▓░  ▒▓▓▓▓ ░▒▓▓▓▓▓▓▓▓▓░                  
 *              ▓▓▒░       ░░▓▓▓▓░░░ ░░░░▒▓▓▓▓               
 *             ▓▓░           ░▓▓░           ░▓▓▓░            
 *            ░▓▓     ░        ▓▓             ░▓▓░           
 *            ░▓▓     ░▓▓     ░▓▓               ▓▓░          
 *    ░▓▓     ░▓▓     ░▓▓    ░▓▓░                ▓▓░         
 *    ░▓▓▓░   ░▓▓     ░▓▓  ░▒▓▓                 ░▒▓▒         
 *      ░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░                    ▓▓         
 *         ░░▒▓▓▓▒▒▒▒▒▒▒░░                        ▓▓▒▓▓▓     
 *            ░▓▓                                 ▓▓▒▒░      
 *            ░▓▓     ░▓▓       ░▓▓▓▓▓▓▓▓░        ▓▓         
 *            ░▓▓     ░▓▓     ░▓▓▓░     ░▓▓▓      ▓▓         
 *            ░▓▓     ░▓▓    ░▓▓          ▒▓▓░    ▓▓         
 *            ░▓▓░░▓▓░░▓▓   ░▓▓            ▓▓░   ░▓▓         
 *             ░░▓▓░░ ░▓▓▓▓▓▓▓▓            ▓▓▓▓▓▓▓▓▓         
 *                                                           
 *
 *     Powered By Elephant
 *
 */

/**
 *  收银台的通知类
 */
public class CashierEventBus extends EventBus{
    private static volatile EventBus defaultInstance;
    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }
}
