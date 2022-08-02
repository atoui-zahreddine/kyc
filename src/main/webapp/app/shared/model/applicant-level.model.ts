import dayjs from 'dayjs';
import { IApplicant } from 'app/shared/model/applicant.model';
import { IStep } from 'app/shared/model/step.model';

export interface IApplicantLevel {
  id?: number;
  code?: string | null;
  levelName?: string | null;
  description?: string | null;
  url?: string | null;
  createdAt?: string | null;
  createdBy?: number | null;
  modifiedAt?: string | null;
  applicant?: IApplicant | null;
  steps?: IStep[] | null;
}

export const defaultValue: Readonly<IApplicantLevel> = {};
