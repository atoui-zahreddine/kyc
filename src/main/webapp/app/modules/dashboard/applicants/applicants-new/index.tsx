import React from 'react';
import { PrimaryButton, Spinner, SpinnerSize, Stack } from '@fluentui/react';
import { useForm } from 'react-hook-form';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import Level from './components/level';
import NewApplicantForm from './components/new-applicant-form';
import './styles.scss';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { createEntity } from 'app/entities/applicant-info/applicant-info.reducer';

const NewApplicant = props => {
  const { updating, updateSuccess } = useAppSelector(state => state.applicant);
  const dispatch = useAppDispatch();
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
    control,
  } = useForm();

  const onSubmit = data => {
    // console.warn(data);
    const applicantAddresses = data?.address
      ? {
          applicantAddresses: [
            {
              state: data.address.city,
              postCode: data.address.zipCode,
              street: data.address.street,
              addresseCountry: {
                name: data.address.country,
              },
            },
          ],
        }
      : null;

    const applicantInfo: IApplicantInfo = {
      firstName: data.firstName,
      lastName: data.lastName,
      dateOfBirth: data.birthDate,
      nationality: data.nationality,
      gender: data.gender,
      ...applicantAddresses,
      countryOfBirth: {
        name: data.countryOfBirth,
      },
      applicant: {
        applicantLevel: data.level,
      },
    };
    console.warn(applicantInfo);
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
        <Level setValue={setValue} register={register} errors={errors} />
        <NewApplicantForm setValue={setValue} register={register} errors={errors} control={control} />
      </div>
    </div>
  );
};

export default NewApplicant;
