import { IApplicant } from 'app/shared/model/applicant.model';

export interface IIpInfo {
  id?: number;
  asn?: number | null;
  asnOrg?: string | null;
  countryCode2?: string | null;
  countryCode3?: string | null;
  ip?: string | null;
  lat?: number | null;
  lon?: number | null;
  applicant?: IApplicant | null;
}

export const defaultValue: Readonly<IIpInfo> = {};
