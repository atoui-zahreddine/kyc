import dayjs from 'dayjs';
import { IApplicant } from 'app/shared/model/applicant.model';
import { ICountry } from 'app/shared/model/country.model';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { IApplicantPhone } from 'app/shared/model/applicant-phone.model';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IApplicantInfo {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  addresses?: string | null;
  email?: string | null;
  middleName?: string | null;
  stateOfBirth?: string | null;
  dateOfBirth?: string | null;
  placeOfBirth?: string | null;
  nationality?: string | null;
  gender?: Gender | null;
  applicant?: IApplicant | null;
  countryOfBirth?: ICountry | null;
  applicantAddresses?: IApplicantAddresse[] | null;
  applicantPhones?: IApplicantPhone[] | null;
  applicantDocs?: IApplicantDocs[] | null;
}

export const defaultValue: Readonly<IApplicantInfo> = {};
