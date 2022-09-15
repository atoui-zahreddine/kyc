import { Text } from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import DocumentCheckResultContainer from './common/document-check-result-container';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

interface IdentityDocumentsProps {
  applicantDocs: IApplicantDocs[];
}
const IdentityDocuments: FunctionComponent<IdentityDocumentsProps> = ({ applicantDocs = [] }) => {
  const identityDocs = applicantDocs.filter(
    doc => doc.docType === TypeDoc.ID_CARD || doc.docType === TypeDoc.PASSPORT || doc.docType === TypeDoc.DRIVERS
  );
  return (
    <div>
      <Text variant="large">Identity Documents</Text>
      {identityDocs.map(doc => (
        <DocumentCheckResultContainer key={doc.id} name={doc.docType} isTwoSides doc={doc} />
      ))}
    </div>
  );
};

export default IdentityDocuments;
