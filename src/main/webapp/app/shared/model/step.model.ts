import dayjs from 'dayjs';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { IDocSet } from 'app/shared/model/doc-set.model';

export interface IStep {
  id?: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  createdAt?: string | null;
  createdBy?: number | null;
  modifiedAt?: string | null;
  applicantLevels?: IApplicantLevel[] | null;
  docSets?: IDocSet[] | null;
}

export const defaultValue: Readonly<IStep> = {};
