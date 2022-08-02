import { IStep } from 'app/shared/model/step.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { SubType } from 'app/shared/model/enumerations/sub-type.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

export interface IDocSet {
  id?: number;
  idDocSetType?: IdDocSetType | null;
  subTypes?: SubType | null;
  types?: TypeDoc | null;
  step?: IStep | null;
}

export const defaultValue: Readonly<IDocSet> = {};
