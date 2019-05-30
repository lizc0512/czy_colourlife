package cn.net.cyberway.protocol;

/**
 * Created by Administrator on 2018/1/25.
 * lizc
 * 主题实体类
 * @Description
 */

public class ThemeEntity {


    /**
     * code : 0
     * message : success
     * content : {"default_theme":{"name":"清爽","navigation":{"id":1,"navi_color":"red","navi_tint_color":"blue","navi_title_color":"yellow","navi_back_image":"http://cimg.colourlife.com/img1","navi_refresh_image":"http://cimg.colourlife.com/img1","navi_close_image":"http://cimg.colourlife.com/img1","navi_setting_image":"http://cimg.colourlife.com/img1","navi_message_image":"http://cimg.colourlife.com/img1","navi_query_image":"http://cimg.colourlife.com/img1","updated_at":1507528342,"creater":"me"},"tabbar":{"updated_at":1507528342,"tabbar_title_color":"#A3AAAE","tabbar_title_select_color":"#F54817","home_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","home_tabbar_title":"首页","home_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","life_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","life_tabbar_title":"生活","life_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","scan_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_richsca@3x.png","linli_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","linli_tabbar_title":"邻里","linli_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","profile_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png","profile_tabbar_title":"我的","profile_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png"}}}
     * contentEncrypt :
     */

    private int code;
    private String message;
    private ContentBean content;
    private String contentEncrypt;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public static class ContentBean {
        /**
         * default_theme : {"name":"清爽","navigation":{"id":1,"navi_color":"red","navi_tint_color":"blue","navi_title_color":"yellow","navi_back_image":"http://cimg.colourlife.com/img1","navi_refresh_image":"http://cimg.colourlife.com/img1","navi_close_image":"http://cimg.colourlife.com/img1","navi_setting_image":"http://cimg.colourlife.com/img1","navi_message_image":"http://cimg.colourlife.com/img1","navi_query_image":"http://cimg.colourlife.com/img1","updated_at":1507528342,"creater":"me"},"tabbar":{"updated_at":1507528342,"tabbar_title_color":"#A3AAAE","tabbar_title_select_color":"#F54817","home_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","home_tabbar_title":"首页","home_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","life_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","life_tabbar_title":"生活","life_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","scan_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_richsca@3x.png","linli_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","linli_tabbar_title":"邻里","linli_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","profile_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png","profile_tabbar_title":"我的","profile_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png"}}
         */

        private DefaultThemeBean default_theme;

        public DefaultThemeBean getDefault_theme() {
            return default_theme;
        }

        public void setDefault_theme(DefaultThemeBean default_theme) {
            this.default_theme = default_theme;
        }

        public static class DefaultThemeBean {
            /**
             * name : 清爽
             * navigation : {"id":1,"navi_color":"red","navi_tint_color":"blue","navi_title_color":"yellow","navi_back_image":"http://cimg.colourlife.com/img1","navi_refresh_image":"http://cimg.colourlife.com/img1","navi_close_image":"http://cimg.colourlife.com/img1","navi_setting_image":"http://cimg.colourlife.com/img1","navi_message_image":"http://cimg.colourlife.com/img1","navi_query_image":"http://cimg.colourlife.com/img1","updated_at":1507528342,"creater":"me"}
             * tabbar : {"updated_at":1507528342,"tabbar_title_color":"#A3AAAE","tabbar_title_select_color":"#F54817","home_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","home_tabbar_title":"首页","home_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png","life_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","life_tabbar_title":"生活","life_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png","scan_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_richsca@3x.png","linli_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","linli_tabbar_title":"邻里","linli_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png","profile_tabbar_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png","profile_tabbar_title":"我的","profile_tabbar_select_image":"http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png"}
             */

            private String name;
            private NavigationBean navigation;
            private TabbarBean tabbar;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public NavigationBean getNavigation() {
                return navigation;
            }

            public void setNavigation(NavigationBean navigation) {
                this.navigation = navigation;
            }

            public TabbarBean getTabbar() {
                return tabbar;
            }

            public void setTabbar(TabbarBean tabbar) {
                this.tabbar = tabbar;
            }

            public static class NavigationBean {
                /**
                 * id : 1
                 * navi_color : red
                 * navi_tint_color : blue
                 * navi_title_color : yellow
                 * navi_back_image : http://cimg.colourlife.com/img1
                 * navi_refresh_image : http://cimg.colourlife.com/img1
                 * navi_close_image : http://cimg.colourlife.com/img1
                 * navi_setting_image : http://cimg.colourlife.com/img1
                 * navi_message_image : http://cimg.colourlife.com/img1
                 * navi_query_image : http://cimg.colourlife.com/img1
                 * updated_at : 1507528342
                 * creater : me
                 */

                private int id;
                private String navi_color;
                private String navi_tint_color;
                private String navi_title_color;
                private String navi_back_image;
                private String navi_refresh_image;
                private String navi_close_image;
                private String navi_setting_image;
                private String navi_message_image;
                private String navi_query_image;
                private int updated_at;
                private String creater;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNavi_color() {
                    return navi_color;
                }

                public void setNavi_color(String navi_color) {
                    this.navi_color = navi_color;
                }

                public String getNavi_tint_color() {
                    return navi_tint_color;
                }

                public void setNavi_tint_color(String navi_tint_color) {
                    this.navi_tint_color = navi_tint_color;
                }

                public String getNavi_title_color() {
                    return navi_title_color;
                }

                public void setNavi_title_color(String navi_title_color) {
                    this.navi_title_color = navi_title_color;
                }

                public String getNavi_back_image() {
                    return navi_back_image;
                }

                public void setNavi_back_image(String navi_back_image) {
                    this.navi_back_image = navi_back_image;
                }

                public String getNavi_refresh_image() {
                    return navi_refresh_image;
                }

                public void setNavi_refresh_image(String navi_refresh_image) {
                    this.navi_refresh_image = navi_refresh_image;
                }

                public String getNavi_close_image() {
                    return navi_close_image;
                }

                public void setNavi_close_image(String navi_close_image) {
                    this.navi_close_image = navi_close_image;
                }

                public String getNavi_setting_image() {
                    return navi_setting_image;
                }

                public void setNavi_setting_image(String navi_setting_image) {
                    this.navi_setting_image = navi_setting_image;
                }

                public String getNavi_message_image() {
                    return navi_message_image;
                }

                public void setNavi_message_image(String navi_message_image) {
                    this.navi_message_image = navi_message_image;
                }

                public String getNavi_query_image() {
                    return navi_query_image;
                }

                public void setNavi_query_image(String navi_query_image) {
                    this.navi_query_image = navi_query_image;
                }

                public int getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(int updated_at) {
                    this.updated_at = updated_at;
                }

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }
            }

            public static class TabbarBean {
                /**
                 * updated_at : 1507528342
                 * tabbar_title_color : #A3AAAE
                 * tabbar_title_select_color : #F54817
                 * home_tabbar_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png
                 * home_tabbar_title : 首页
                 * home_tabbar_select_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_home@3x.png
                 * life_tabbar_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png
                 * life_tabbar_title : 生活
                 * life_tabbar_select_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_life@3x.png
                 * scan_tabbar_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_richsca@3x.png
                 * linli_tabbar_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png
                 * linli_tabbar_title : 邻里
                 * linli_tabbar_select_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_lil@3x.png
                 * profile_tabbar_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png
                 * profile_tabbar_title : 我的
                 * profile_tabbar_select_image : http://colourhome-czytest.colourlife.com//resources/imgs/newyear/icon_tab_more@3x.png
                 */

                private int updated_at;
                private String tabbar_title_color;
                private String tabbar_title_select_color;
                private String home_tabbar_image;
                private String home_tabbar_title;
                private String home_tabbar_select_image;
                private String life_tabbar_image;
                private String life_tabbar_title;
                private String life_tabbar_select_image;
                private String scan_tabbar_image;
                private String linli_tabbar_image;
                private String linli_tabbar_title;
                private String linli_tabbar_select_image;
                private String profile_tabbar_image;
                private String profile_tabbar_title;
                private String profile_tabbar_select_image;

                public int getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(int updated_at) {
                    this.updated_at = updated_at;
                }

                public String getTabbar_title_color() {
                    return tabbar_title_color;
                }

                public void setTabbar_title_color(String tabbar_title_color) {
                    this.tabbar_title_color = tabbar_title_color;
                }

                public String getTabbar_title_select_color() {
                    return tabbar_title_select_color;
                }

                public void setTabbar_title_select_color(String tabbar_title_select_color) {
                    this.tabbar_title_select_color = tabbar_title_select_color;
                }

                public String getHome_tabbar_image() {
                    return home_tabbar_image;
                }

                public void setHome_tabbar_image(String home_tabbar_image) {
                    this.home_tabbar_image = home_tabbar_image;
                }

                public String getHome_tabbar_title() {
                    return home_tabbar_title;
                }

                public void setHome_tabbar_title(String home_tabbar_title) {
                    this.home_tabbar_title = home_tabbar_title;
                }

                public String getHome_tabbar_select_image() {
                    return home_tabbar_select_image;
                }

                public void setHome_tabbar_select_image(String home_tabbar_select_image) {
                    this.home_tabbar_select_image = home_tabbar_select_image;
                }

                public String getLife_tabbar_image() {
                    return life_tabbar_image;
                }

                public void setLife_tabbar_image(String life_tabbar_image) {
                    this.life_tabbar_image = life_tabbar_image;
                }

                public String getLife_tabbar_title() {
                    return life_tabbar_title;
                }

                public void setLife_tabbar_title(String life_tabbar_title) {
                    this.life_tabbar_title = life_tabbar_title;
                }

                public String getLife_tabbar_select_image() {
                    return life_tabbar_select_image;
                }

                public void setLife_tabbar_select_image(String life_tabbar_select_image) {
                    this.life_tabbar_select_image = life_tabbar_select_image;
                }

                public String getScan_tabbar_image() {
                    return scan_tabbar_image;
                }

                public void setScan_tabbar_image(String scan_tabbar_image) {
                    this.scan_tabbar_image = scan_tabbar_image;
                }

                public String getLinli_tabbar_image() {
                    return linli_tabbar_image;
                }

                public void setLinli_tabbar_image(String linli_tabbar_image) {
                    this.linli_tabbar_image = linli_tabbar_image;
                }

                public String getLinli_tabbar_title() {
                    return linli_tabbar_title;
                }

                public void setLinli_tabbar_title(String linli_tabbar_title) {
                    this.linli_tabbar_title = linli_tabbar_title;
                }

                public String getLinli_tabbar_select_image() {
                    return linli_tabbar_select_image;
                }

                public void setLinli_tabbar_select_image(String linli_tabbar_select_image) {
                    this.linli_tabbar_select_image = linli_tabbar_select_image;
                }

                public String getProfile_tabbar_image() {
                    return profile_tabbar_image;
                }

                public void setProfile_tabbar_image(String profile_tabbar_image) {
                    this.profile_tabbar_image = profile_tabbar_image;
                }

                public String getProfile_tabbar_title() {
                    return profile_tabbar_title;
                }

                public void setProfile_tabbar_title(String profile_tabbar_title) {
                    this.profile_tabbar_title = profile_tabbar_title;
                }

                public String getProfile_tabbar_select_image() {
                    return profile_tabbar_select_image;
                }

                public void setProfile_tabbar_select_image(String profile_tabbar_select_image) {
                    this.profile_tabbar_select_image = profile_tabbar_select_image;
                }
            }
        }
    }
}
