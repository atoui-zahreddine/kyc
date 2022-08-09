import { IColumn } from '@fluentui/react/lib/DetailsList';

export interface IDetailsListDocumentsExampleState {
  columns: IColumn[];
  items: IDocument[];
  selectionDetails: string;
  isModalSelection: boolean;
  isCompactMode: boolean;
  announcedMessage?: string;
}

export interface IDocument {
  levelName?: string;
  createdAt?: string;
  createdBy?: number;
  steps?: number;
}
