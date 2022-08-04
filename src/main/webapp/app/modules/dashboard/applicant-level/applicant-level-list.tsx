import React, { useState, useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { PrimaryButton } from '@fluentui/react';

export const ApplicantLevelList = (props: RouteComponentProps<{ url: string }>) => {
  return (
    <div>
      <PrimaryButton>Applicant Levels</PrimaryButton>
    </div>
  );
};

export default ApplicantLevelList;
