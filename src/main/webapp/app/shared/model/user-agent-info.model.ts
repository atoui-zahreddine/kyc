import { IApplicant } from 'app/shared/model/applicant.model';

export interface IUserAgentInfo {
  id?: number;
  uaBrowser?: string | null;
  uaBrowserVersion?: string | null;
  uaDeviceType?: string | null;
  uaPlatform?: string | null;
  applicant?: IApplicant | null;
}

export const defaultValue: Readonly<IUserAgentInfo> = {};
