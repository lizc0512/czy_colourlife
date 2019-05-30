package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/14 17:30
 * @change
 * @chang time
 * @class describe
 */

public class PayResultEntity extends BaseContentEntity {
    /**
     * content : {"appId":"323521861252157440","timestamp":1513238894000,"version":"1.0","bizContent":"yQKYjH8qsvs6s6Mh3oI3mREoUeeMI5yTy+4n6eCyugAN0l+kf4f0SGzMEsdcjlpefRLk78gS69wbeogl3QwAA2OOerFQ45YL3l4sVIJST4LK5EIZkTGcxchg94SAmAUpBP3XNV\/6fasrjRYlnQUk6mfeTvOfzt2qV+1p2NooRIfMKKQdK0+luMEnSqVJqSZVs9OowfpFaC2evLcSudvDY\/+8FDEhLjpmVZkdiqQKIXNwZf323IZREMiDOhkQ1FARRuEWzbM\/Bxb4ejNBLelEYPBQeuJ19SALnJoAUfA16TV9XMPnLgWfqjTs\/Li64h5YifMWY784hdmbAxD2uAp6vlEnI6gbWyuQ6gHa1OPKT9njeH98kvRBP2Ys3ML9zk0h3p9wNKdtlQZ7K6yvTtntkGO3pG2di+YuHrB9eNnb8SfSB7MbLjnlljN\/pyIV+J4fK02aji1K0ikOvZi5Ip8zvM+04WUxDNmI5RX5kqlMcm5\/pbNMBom5yCQO45TkHYFQVK6vx+xh0fHDsaQB6swsFlw3uTf1CHoHg\/idsYwrAf3EiFb4XRaeC8sUqBml4thuA8Jo8oO7MNmysCsqjdCf4t+m6mPLewAQL0kBK+qaVQPnLhrzeQQuY30eHi+aKvWp\/o74cSfPewntUdHa4ympa4k9hgrl27UP2SdDIWA01Z0vHYdekHr6nAntN5I11I6pkOM3mVWpxzdjkfVCYAse5Qdrnw0k\/GbH3DQp30XiCotORc4M5ixAJyVJWCItxRhyxF+Dn++qcMFMHdadM30KfWT0yoNwf2gqW6Dx1Lqr6IjMPjXD8ZA1N8fmt6WHncZpy1B2H6ucGouCXGR2Uy4R5g==","type":1,"channel":"FANPIAO","outUserId":1288746,"sign":"0zn9ICQ3Y1irZdVtyu7CNS\/Ich0pZ3b1n\/3rpjXoEYybPaOjwgu9dv3D8566vQ6nrrUadyBf7ZZuc6AuNJUA+DvFOH2QOqDbIHe7yR9EqxI3p9QOVtc0xoWThMBTlT8pcDTuQTJaj5DSOYLAIqDPn3h8C3mhPNglCdhIARqwrjc="}
     * contentEncrypt :
     */

    private String content;
    private String contentEncrypt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }
}
