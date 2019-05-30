package cn.net.cyberway.home.entity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/16 9:09
 * @change
 * @chang time
 * @class describe
 */
public class BehaviorEntity {
    private String refer_module;
    private String app_code;
    private long entry_time;
    private long departure_time;
    private int exit_app;
    private String refer_page;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    private String app_name;

    public String getRefer_module() {
        return refer_module;
    }

    public void setRefer_module(String refer_module) {
        this.refer_module = refer_module;
    }

    public String getApp_code() {
        return app_code;
    }

    public void setApp_code(String app_code) {
        this.app_code = app_code;
    }

    public long getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(long entry_time) {
        this.entry_time = entry_time;
    }

    public long getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(long departure_time) {
        this.departure_time = departure_time;
    }

    public int getExit_app() {
        return exit_app;
    }

    public void setExit_app(int exit_app) {
        this.exit_app = exit_app;
    }


    public String getRefer_page() {
        return refer_page;
    }

    public void setRefer_page(String refer_page) {
        this.refer_page = refer_page;
    }
}
