import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { IApplicantPhone } from 'app/shared/model/applicant-phone.model';
import { CountryRegion } from 'app/shared/model/enumerations/country-region.model';

export interface ICountry {
  id?: number;
  name?: string | null;
  countryCode2?: string | null;
  countryCode3?: string | null;
  phoneCode?: string | null;
  region?: CountryRegion | null;
  addresses?: IApplicantAddresse | null;
  docs?: IApplicantDocs | null;
  applicants?: IApplicantInfo | null;
  phones?: IApplicantPhone | null;
}

export const defaultValue: Readonly<ICountry> = {};
