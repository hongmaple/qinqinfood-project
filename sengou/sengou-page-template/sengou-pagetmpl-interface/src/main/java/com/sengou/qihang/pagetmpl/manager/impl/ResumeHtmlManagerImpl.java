package com.sengou.qihang.pagetmpl.manager.impl;

import com.sengou.qihang.pagetmpl.client.ResumeHtmlClient;
import com.sengou.qihang.pagetmpl.manager.ResumeHtmlManager;
import org.springframework.stereotype.Service;

/**
 * @author maple
 * @date 2020/8/15
 */
@Service
public class ResumeHtmlManagerImpl implements ResumeHtmlManager {
    private final ResumeHtmlClient resumeHtmlClient;

    public ResumeHtmlManagerImpl(ResumeHtmlClient resumeHtmlClient) {
        this.resumeHtmlClient = resumeHtmlClient;
    }

    @Override
    public String toResumePageFile(Long skuId) {
        return resumeHtmlClient.toResumePageFile(skuId);
    }

    @Override
    public void deleteHtml(Long skuId) {
        resumeHtmlClient.deleteHtml(skuId);
    }
}
