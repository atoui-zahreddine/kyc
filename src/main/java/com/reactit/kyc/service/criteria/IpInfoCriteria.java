package com.reactit.kyc.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.reactit.kyc.domain.IpInfo} entity. This class is used
 * in {@link com.reactit.kyc.web.rest.IpInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ip-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IpInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter asn;

    private StringFilter asnOrg;

    private StringFilter countryCode2;

    private StringFilter countryCode3;

    private StringFilter ip;

    private DoubleFilter lat;

    private DoubleFilter lon;

    private LongFilter applicantId;

    private Boolean distinct;

    public IpInfoCriteria() {}

    public IpInfoCriteria(IpInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.asn = other.asn == null ? null : other.asn.copy();
        this.asnOrg = other.asnOrg == null ? null : other.asnOrg.copy();
        this.countryCode2 = other.countryCode2 == null ? null : other.countryCode2.copy();
        this.countryCode3 = other.countryCode3 == null ? null : other.countryCode3.copy();
        this.ip = other.ip == null ? null : other.ip.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.lon = other.lon == null ? null : other.lon.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IpInfoCriteria copy() {
        return new IpInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAsn() {
        return asn;
    }

    public LongFilter asn() {
        if (asn == null) {
            asn = new LongFilter();
        }
        return asn;
    }

    public void setAsn(LongFilter asn) {
        this.asn = asn;
    }

    public StringFilter getAsnOrg() {
        return asnOrg;
    }

    public StringFilter asnOrg() {
        if (asnOrg == null) {
            asnOrg = new StringFilter();
        }
        return asnOrg;
    }

    public void setAsnOrg(StringFilter asnOrg) {
        this.asnOrg = asnOrg;
    }

    public StringFilter getCountryCode2() {
        return countryCode2;
    }

    public StringFilter countryCode2() {
        if (countryCode2 == null) {
            countryCode2 = new StringFilter();
        }
        return countryCode2;
    }

    public void setCountryCode2(StringFilter countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public StringFilter getCountryCode3() {
        return countryCode3;
    }

    public StringFilter countryCode3() {
        if (countryCode3 == null) {
            countryCode3 = new StringFilter();
        }
        return countryCode3;
    }

    public void setCountryCode3(StringFilter countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public StringFilter getIp() {
        return ip;
    }

    public StringFilter ip() {
        if (ip == null) {
            ip = new StringFilter();
        }
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public DoubleFilter getLat() {
        return lat;
    }

    public DoubleFilter lat() {
        if (lat == null) {
            lat = new DoubleFilter();
        }
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public DoubleFilter getLon() {
        return lon;
    }

    public DoubleFilter lon() {
        if (lon == null) {
            lon = new DoubleFilter();
        }
        return lon;
    }

    public void setLon(DoubleFilter lon) {
        this.lon = lon;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public LongFilter applicantId() {
        if (applicantId == null) {
            applicantId = new LongFilter();
        }
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IpInfoCriteria that = (IpInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(asn, that.asn) &&
            Objects.equals(asnOrg, that.asnOrg) &&
            Objects.equals(countryCode2, that.countryCode2) &&
            Objects.equals(countryCode3, that.countryCode3) &&
            Objects.equals(ip, that.ip) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(lon, that.lon) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, asn, asnOrg, countryCode2, countryCode3, ip, lat, lon, applicantId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IpInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (asn != null ? "asn=" + asn + ", " : "") +
            (asnOrg != null ? "asnOrg=" + asnOrg + ", " : "") +
            (countryCode2 != null ? "countryCode2=" + countryCode2 + ", " : "") +
            (countryCode3 != null ? "countryCode3=" + countryCode3 + ", " : "") +
            (ip != null ? "ip=" + ip + ", " : "") +
            (lat != null ? "lat=" + lat + ", " : "") +
            (lon != null ? "lon=" + lon + ", " : "") +
            (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
