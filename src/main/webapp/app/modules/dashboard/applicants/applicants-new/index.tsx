import React, { useEffect, useState } from 'react';
import { PrimaryButton, Spinner, SpinnerSize, Stack } from '@fluentui/react';
import { useForm } from 'react-hook-form';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import Level from './components/level';
import NewApplicantForm from './components/new-applicant-form';
import './styles.scss';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import axios from 'axios';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';
import { createEntity, updateEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import { Redirect } from 'react-router-dom';

const uploadDocs = async (level: IApplicantLevel, data, applicantInfoId) => {
  const applicantDocs: IApplicantDocs[] = [];

  for (const step of level.steps)
    for (const docSet of step.docSets) {
      try {
        const fileToUpload = data.files[`${IdDocSetType[docSet.idDocSetType]}`][`${TypeDoc[docSet.types]}`];
        console.warn(fileToUpload);
        console.warn(data);
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

const NewApplicant = ({ match }) => {
  const { updating, updateSuccess, entity } = useAppSelector(state => state.applicantInfo);
  const dispatch = useAppDispatch();
  const { handleSubmit, setValue, control, register, getValues } = useForm();
  const [isApplicantInfoDocsUpdated, setIsApplicantInfoDocsUpdated] = useState(false);
  const [shouldUploadDocs, setShouldUploadDocs] = useState(false);

  const onSubmit = data => {
    const applicantAddresses = data[`${IdDocSetType.PROOF_OF_RESIDENCE}`]
      ? {
          applicantAddresses: [
            {
              ...data[`${IdDocSetType.PROOF_OF_RESIDENCE}`],
            },
          ] as IApplicantAddresse[],
        }
      : null;

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

  async function updateApplicantInfoDocs() {
    if (shouldUploadDocs && entity.applicantDocs?.length === 0) {
      console.warn('updateApplicantInfoDocs ...');
      const data = getValues();
      const applicantDocs = await uploadDocs(data.level, data, entity.id);
      const newApplicantInfo: IApplicantInfo = { ...entity, applicantDocs };
      dispatch(updateEntity(newApplicantInfo));
      setIsApplicantInfoDocsUpdated(true);
    }
  }

  useEffect(() => {
    if (isApplicantInfoDocsUpdated) return;
    updateApplicantInfoDocs().catch(e => console.error(e));
  }, [entity]);

  if (updateSuccess && isApplicantInfoDocsUpdated) return <Redirect to={`${match.url.replace('new', 'screening')}/${entity.id}`} />;

  return (
    <div>
      <Stack verticalFill styles={{ root: { height: 50 } }}>
        <PrimaryButton onClick={handleSubmit(onSubmit)} styles={{ root: { alignSelf: 'flex-end' } }}>
          {!updating ? 'Submit' : <Spinner size={SpinnerSize.medium} />}
        </PrimaryButton>
      </Stack>
      <div className="new-applicant">
        <Level setValue={setValue} register={register} />
        <NewApplicantForm setValue={setValue} control={control} />
      </div>
    </div>
  );
};

export default NewApplicant;
