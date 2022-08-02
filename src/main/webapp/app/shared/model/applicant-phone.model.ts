import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { ICountry } from 'app/shared/model/country.model';

export interface IApplicantPhone {
  id?: number;
  country?: string | null;
  number?: string | null;
  enabled?: boolean | null;
  applicantInfo?: IApplicantInfo | null;
  phoneCountries?: ICountry[] | null;
}

export const defaultValue: Readonly<IApplicantPhone> = {
  enabled: false,
};
