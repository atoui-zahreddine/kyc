import dayjs from 'dayjs';
import { IDocSet } from 'app/shared/model/doc-set.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';

export interface IStep {
  id?: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  createdAt?: string | null;
  createdBy?: number | null;
  modifiedAt?: string | null;
  docSets?: IDocSet[] | null;
  applicantLevels?: IApplicantLevel[] | null;
}

export const defaultValue: Readonly<IStep> = {};
