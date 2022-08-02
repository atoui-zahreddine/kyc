import dayjs from 'dayjs';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { ICountry } from 'app/shared/model/country.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';
import { SubType } from 'app/shared/model/enumerations/sub-type.model';

export interface IApplicantDocs {
  id?: number;
  docType?: TypeDoc | null;
  firstName?: string | null;
  lastName?: string | null;
  number?: string | null;
  dateOfBirth?: string | null;
  validUntil?: string | null;
  imageUrl?: string | null;
  subTypes?: SubType | null;
  imageTrust?: string | null;
  applicantInfo?: IApplicantInfo | null;
  docsCountries?: ICountry[] | null;
}

export const defaultValue: Readonly<IApplicantDocs> = {};
