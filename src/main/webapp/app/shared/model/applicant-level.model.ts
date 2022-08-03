import dayjs from 'dayjs';
import { IStep } from 'app/shared/model/step.model';
import { IApplicant } from 'app/shared/model/applicant.model';

export interface IApplicantLevel {
  id?: number;
  code?: string | null;
  levelName?: string | null;
  description?: string | null;
  url?: string | null;
  createdAt?: string | null;
  createdBy?: number | null;
  modifiedAt?: string | null;
  steps?: IStep[] | null;
  applicants?: IApplicant[] | null;
}

export const defaultValue: Readonly<IApplicantLevel> = {};
