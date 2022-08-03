package com.reactit.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A IpInfo.
 */
@Entity
@Table(name = "ip_info")
public class IpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "asn")
    private Long asn;

    @Column(name = "asn_org")
    private String asnOrg;

    @Column(name = "country_code_2")
    private String countryCode2;

    @Column(name = "country_code_3")
    private String countryCode3;

    @Column(name = "ip")
    private String ip;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @JsonIgnoreProperties(value = { "applicantLevel", "applicantInfo", "ipInfo", "userAgentInfo" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IpInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAsn() {
        return this.asn;
    }

    public IpInfo asn(Long asn) {
        this.setAsn(asn);
        return this;
    }

    public void setAsn(Long asn) {
        this.asn = asn;
    }

    public String getAsnOrg() {
        return this.asnOrg;
    }

    public IpInfo asnOrg(String asnOrg) {
        this.setAsnOrg(asnOrg);
        return this;
    }

    public void setAsnOrg(String asnOrg) {
        this.asnOrg = asnOrg;
    }

    public String getCountryCode2() {
        return this.countryCode2;
    }

    public IpInfo countryCode2(String countryCode2) {
        this.setCountryCode2(countryCode2);
        return this;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public String getCountryCode3() {
        return this.countryCode3;
    }

    public IpInfo countryCode3(String countryCode3) {
        this.setCountryCode3(countryCode3);
        return this;
    }

    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public String getIp() {
        return this.ip;
    }

    public IpInfo ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getLat() {
        return this.lat;
    }

    public IpInfo lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public IpInfo lon(Double lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public IpInfo applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IpInfo)) {
            return false;
        }
        return id != null && id.equals(((IpInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IpInfo{" +
            "id=" + getId() +
            ", asn=" + getAsn() +
            ", asnOrg='" + getAsnOrg() + "'" +
            ", countryCode2='" + getCountryCode2() + "'" +
            ", countryCode3='" + getCountryCode3() + "'" +
            ", ip='" + getIp() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            "}";
    }
}
