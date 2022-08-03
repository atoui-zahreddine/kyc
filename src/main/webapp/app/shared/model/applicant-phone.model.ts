import { ICountry } from 'app/shared/model/country.model';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';

export interface IApplicantPhone {
  id?: number;
  country?: string | null;
  number?: string | null;
  enabled?: boolean | null;
  phoneCountry?: ICountry | null;
  applicantInfos?: IApplicantInfo[] | null;
}

export const defaultValue: Readonly<IApplicantPhone> = {
  enabled: false,
};
