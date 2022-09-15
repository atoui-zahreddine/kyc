import React, { useEffect, useState } from 'react';
import { PrimaryButton, Spinner, SpinnerSize, Stack } from '@fluentui/react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { Redirect } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import Level from './components/level';
import NewApplicantForm from './components/new-applicant-form';
import { createEntity, updateEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

import './styles.scss';

const NewApplicant = ({ match }) => {
  const { updating, updateSuccess, entity } = useAppSelector(state => state.applicantInfo);
  const { handleSubmit, setValue, control, register, getValues } = useForm();
  const [isApplicantInfoDocsUpdated, setIsApplicantInfoDocsUpdated] = useState(false);
  const [shouldUploadDocs, setShouldUploadDocs] = useState(false);
  const dispatch = useAppDispatch();

  const onSubmit = data => {
    const applicantAddresses = getApplicantAddress(data);

    const applicantInfo: IApplicantInfo = {
      firstName: data.firstName,
      lastName: data.lastName,
      dateOfBirth: data.birthDate,
      nationality: data.nationality,
      gender: data.gender,
      email: data.email,
      ...applicantAddresses,
      applicantPhones: [
        {
          number: data.phone,
        },
      ],
      countryOfBirth: {
        name: data.countryOfBirth,
      },
      applicant: {
        applicantLevel: data.level,
      },
    };

    dispatch(createEntity(applicantInfo));
    setShouldUploadDocs(true);
  };

  const updateApplicantInfoDocs = async () => {
    if (shouldUploadDocs && entity.applicantDocs?.length === 0) {
      const data = getValues();
      const applicantDocs = await uploadDocs(data.level, data, entity.id);
      const newApplicantInfo: IApplicantInfo = { ...entity, applicantDocs };
      dispatch(updateEntity(newApplicantInfo));
      setIsApplicantInfoDocsUpdated(true);
    }
  };

  // update applicant info docs after the applicant info is created
  useEffect(() => {
    if (isApplicantInfoDocsUpdated) return;
    updateApplicantInfoDocs().catch(e => console.error(e));
  }, [entity]);

  if (updateSuccess && isApplicantInfoDocsUpdated) return <Redirect to={`${match.url.replace('new', 'details')}/${entity.id}`} />;

  return (
    <div>
      <Stack verticalFill styles={{ root: { height: 50 } }}>
        <PrimaryButton onClick={handleSubmit(onSubmit)} styles={{ root: { alignSelf: 'flex-end' } }}>
          {!updating ? 'Submit' : <Spinner size={SpinnerSize.medium} />}
        </PrimaryButton>
      </Stack>
      <div className="new-applicant">
        <Level setValue={setValue} control={control} />
        <NewApplicantForm setValue={setValue} control={control} />
      </div>
    </div>
  );
};

const uploadDocs = async (level: IApplicantLevel, data, applicantInfoId) => {
  const applicantDocs: IApplicantDocs[] = [];

  for (const step of level.steps)
    for (const docSet of step.docSets) {
      try {
        const fileToUpload = data.files[`${IdDocSetType[docSet.idDocSetType]}`][`${TypeDoc[docSet.types]}`];
        if (!fileToUpload.file) {
          continue;
        }
        const file = new FormData();
        file.append('file', fileToUpload.file);
        file.append('prePath', `${IdDocSetType[docSet.idDocSetType]}/${TypeDoc[docSet.types]}/${applicantInfoId}`);
        const headers = {
          'content-type': 'multipart/form-data',
        };
        const result = await axios.post('/api/files', file, { headers });
        applicantDocs.push({
          docType: docSet.types,
          imageUrl: result.data.url,
          docsCountry: { name: data.files[`${IdDocSetType[docSet.idDocSetType]}`][`${TypeDoc[docSet.types]}`].country },
        });
      } catch (e) {
        console.error(e);
      }
    }
  return applicantDocs;
};

const getApplicantAddress = data => {
  return data[`${IdDocSetType.PROOF_OF_RESIDENCE}`]
    ? {
        applicantAddresses: [
          {
            ...data[`${IdDocSetType.PROOF_OF_RESIDENCE}`],
          },
        ] as IApplicantAddresse[],
      }
    : null;
};

export default NewApplicant;
