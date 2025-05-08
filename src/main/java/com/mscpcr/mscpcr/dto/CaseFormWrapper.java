package com.mscpcr.mscpcr.dto;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.LegalCase;

public class CaseFormWrapper {

    private LegalCase legalCase = new LegalCase();
    private DcpuCaseDetail dcpuCaseDetail = new DcpuCaseDetail();

    public LegalCase getLegalCase() {
        return legalCase;
    }

    public void setLegalCase(LegalCase legalCase) {
        this.legalCase = legalCase;
    }

    public DcpuCaseDetail getDcpuCaseDetail() {
        return dcpuCaseDetail;
    }

    public void setDcpuCaseDetail(DcpuCaseDetail dcpuCaseDetail) {
        this.dcpuCaseDetail = dcpuCaseDetail;
    }
}
