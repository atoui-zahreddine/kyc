package com.reactit.kyc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.kyc.domain.IpInfo} entity.
 */
public class IpInfoDTO implements Serializable {

    private Long id;

    private Long asn;

    private String asnOrg;

    private String countryCode2;

    private String countryCode3;

    private String ip;

    private Double lat;

    private Double lon;

    private ApplicantDTO applicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAsn() {
        return asn;
    }

    public void setAsn(Long asn) {
        this.asn = asn;
    }

    public String getAsnOrg() {
        return asnOrg;
    }

    public void setAsnOrg(String asnOrg) {
        this.asnOrg = asnOrg;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public String getCountryCode3() {
        return countryCode3;
    }

    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IpInfoDTO)) {
            return false;
        }

        IpInfoDTO ipInfoDTO = (IpInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ipInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IpInfoDTO{" +
            "id=" + getId() +
            ", asn=" + getAsn() +
            ", asnOrg='" + getAsnOrg() + "'" +
            ", countryCode2='" + getCountryCode2() + "'" +
            ", countryCode3='" + getCountryCode3() + "'" +
            ", ip='" + getIp() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", applicant=" + getApplicant() +
            "}";
    }
}
