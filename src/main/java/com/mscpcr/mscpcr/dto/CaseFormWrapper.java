package com.mscpcr.mscpcr.dto;

import com.mscpcr.mscpcr.entity.DcpuCaseDetail;
import com.mscpcr.mscpcr.entity.Legalcase;

public class CaseFormWrapper {

    private Legalcase legalcase = new Legalcase();
    private DcpuCaseDetail dcpuCaseDetail = new DcpuCaseDetail();

    public Legalcase getLegalcase() {
        return legalcase;
    }

    public void setLegalcase(Legalcase legalcase) {
        this.legalcase = legalcase;
    }

    public DcpuCaseDetail getDcpuCaseDetail() {
        return dcpuCaseDetail;
    }

    public void setDcpuCaseDetail(DcpuCaseDetail dcpuCaseDetail) {
        this.dcpuCaseDetail = dcpuCaseDetail;
    }
}
