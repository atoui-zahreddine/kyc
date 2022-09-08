import React from 'react';
import { PrimaryButton, Spinner, SpinnerSize, Stack } from '@fluentui/react';
import { useForm } from 'react-hook-form';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import Level from './components/level';
import NewApplicantForm from './components/new-applicant-form';
import './styles.scss';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { createEntity } from 'app/entities/applicant-info/applicant-info.reducer';

const NewApplicant = () => {
  const { updating, updateSuccess } = useAppSelector(state => state.applicant);
  const dispatch = useAppDispatch();
  const { handleSubmit, setValue, control, register } = useForm();

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
  };

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
