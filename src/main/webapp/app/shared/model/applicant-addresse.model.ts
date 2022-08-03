import { ICountry } from 'app/shared/model/country.model';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';

export interface IApplicantAddresse {
  id?: number;
  postCode?: string | null;
  state?: string | null;
  street?: string | null;
  subStreet?: string | null;
  town?: string | null;
  enabled?: boolean | null;
  addresseCountry?: ICountry | null;
  applicantInfos?: IApplicantInfo[] | null;
}

export const defaultValue: Readonly<IApplicantAddresse> = {
  enabled: false,
};
