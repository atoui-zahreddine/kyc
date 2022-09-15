import { Text } from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import DocumentCheckResultContainer from './common/document-check-result-container';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

interface LivenessProps {
  applicantDocs: IApplicantDocs[];
}

const Liveness: FunctionComponent<LivenessProps> = ({ applicantDocs = [] }) => {
  const livenessDocs = applicantDocs.filter(doc => doc.docType === TypeDoc.SELFIE);
  return (
    <div>
      <Text variant="large">Liveness</Text>
      {livenessDocs.map(doc => (
        <DocumentCheckResultContainer key={doc.id} doc={doc} name="Selfie" />
      ))}
    </div>
  );
};

export default Liveness;
