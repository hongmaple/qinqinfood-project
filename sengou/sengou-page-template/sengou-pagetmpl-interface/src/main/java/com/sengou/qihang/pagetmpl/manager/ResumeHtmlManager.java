package com.sengou.qihang.pagetmpl.manager;

public interface ResumeHtmlManager {
    String toResumePageFile(Long skuId);

    void deleteHtml(Long skuId);
}
