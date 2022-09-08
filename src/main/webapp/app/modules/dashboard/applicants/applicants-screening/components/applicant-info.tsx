import { Label, Stack, Text } from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';

interface ApplicantInfoProps {
  applicantInfo: IApplicantInfo;
}

const ApplicantInfo: FunctionComponent<ApplicantInfoProps> = ({ applicantInfo }) => {
  return (
    <div>
      <Text variant="large">Details</Text>
      <Stack horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
        <Label>First Name: {applicantInfo && applicantInfo.firstName}</Label>
        <Label>Last Name: {applicantInfo && applicantInfo.lastName}</Label>
        <Label>Date of Birth: {applicantInfo && applicantInfo.dateOfBirth}</Label>
        <Label>gender: {applicantInfo && applicantInfo.gender}</Label>
        <Label>Nationality: {applicantInfo && applicantInfo.nationality}</Label>
        <Label>country of Birth: {applicantInfo && applicantInfo.countryOfBirth?.name}</Label>
      </Stack>
    </div>
  );
};

export default ApplicantInfo;
