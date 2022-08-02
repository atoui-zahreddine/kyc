import dayjs from 'dayjs';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { IIpInfo } from 'app/shared/model/ip-info.model';
import { IUserAgentInfo } from 'app/shared/model/user-agent-info.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { Platform } from 'app/shared/model/enumerations/platform.model';

export interface IApplicant {
  id?: number;
  createdAt?: string | null;
  createdBy?: number | null;
  modifiedAt?: string | null;
  platform?: Platform | null;
  applicantInfo?: IApplicantInfo | null;
  ipInfo?: IIpInfo | null;
  userAgentInfo?: IUserAgentInfo | null;
  applicantLevels?: IApplicantLevel[] | null;
}

export const defaultValue: Readonly<IApplicant> = {};
