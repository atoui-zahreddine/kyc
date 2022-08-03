import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities as getApplicants } from 'app/entities/applicant/applicant.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { getEntities as getApplicantAddresses } from 'app/entities/applicant-addresse/applicant-addresse.reducer';
import { IApplicantPhone } from 'app/shared/model/applicant-phone.model';
import { getEntities as getApplicantPhones } from 'app/entities/applicant-phone/applicant-phone.reducer';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { getEntities as getApplicantDocs } from 'app/entities/applicant-docs/applicant-docs.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant-info.reducer';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export const ApplicantInfoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicants = useAppSelector(state => state.applicant.entities);
  const countries = useAppSelector(state => state.country.entities);
  const applicantAddresses = useAppSelector(state => state.applicantAddresse.entities);
  const applicantPhones = useAppSelector(state => state.applicantPhone.entities);
  const applicantDocs = useAppSelector(state => state.applicantDocs.entities);
  const applicantInfoEntity = useAppSelector(state => state.applicantInfo.entity);
  const loading = useAppSelector(state => state.applicantInfo.loading);
  const updating = useAppSelector(state => state.applicantInfo.updating);
  const updateSuccess = useAppSelector(state => state.applicantInfo.updateSuccess);
  const genderValues = Object.keys(Gender);
  const handleClose = () => {
    props.history.push('/applicant-info' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplicants({}));
    dispatch(getCountries({}));
    dispatch(getApplicantAddresses({}));
    dispatch(getApplicantPhones({}));
    dispatch(getApplicantDocs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...applicantInfoEntity,
      ...values,
      applicantAddresses: mapIdList(values.applicantAddresses),
      applicantPhones: mapIdList(values.applicantPhones),
      applicantDocs: mapIdList(values.applicantDocs),
      applicant: applicants.find(it => it.id.toString() === values.applicant.toString()),
      countryOfBirth: countries.find(it => it.id.toString() === values.countryOfBirth.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          gender: 'MALE',
          ...applicantInfoEntity,
          applicant: applicantInfoEntity?.applicant?.id,
          countryOfBirth: applicantInfoEntity?.countryOfBirth?.id,
          applicantAddresses: applicantInfoEntity?.applicantAddresses?.map(e => e.id.toString()),
          applicantPhones: applicantInfoEntity?.applicantPhones?.map(e => e.id.toString()),
          applicantDocs: applicantInfoEntity?.applicantDocs?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicantInfo.home.createOrEditLabel" data-cy="ApplicantInfoCreateUpdateHeading">
            Create or edit a ApplicantInfo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="applicant-info-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="First Name" id="applicant-info-firstName" name="firstName" data-cy="firstName" type="text" />
              <ValidatedField label="Last Name" id="applicant-info-lastName" name="lastName" data-cy="lastName" type="text" />
              <ValidatedField label="Addresses" id="applicant-info-addresses" name="addresses" data-cy="addresses" type="text" />
              <ValidatedField label="Email" id="applicant-info-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Middle Name" id="applicant-info-middleName" name="middleName" data-cy="middleName" type="text" />
              <ValidatedField
                label="State Of Birth"
                id="applicant-info-stateOfBirth"
                name="stateOfBirth"
                data-cy="stateOfBirth"
                type="text"
              />
              <ValidatedField label="Date Of Birth" id="applicant-info-dateOfBirth" name="dateOfBirth" data-cy="dateOfBirth" type="date" />
              <ValidatedField
                label="Place Of Birth"
                id="applicant-info-placeOfBirth"
                name="placeOfBirth"
                data-cy="placeOfBirth"
                type="text"
              />
              <ValidatedField label="Nationality" id="applicant-info-nationality" name="nationality" data-cy="nationality" type="text" />
              <ValidatedField label="Gender" id="applicant-info-gender" name="gender" data-cy="gender" type="select">
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {gender}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="applicant-info-applicant" name="applicant" data-cy="applicant" label="Applicant" type="select">
                <option value="" key="0" />
                {applicants
                  ? applicants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="applicant-info-countryOfBirth"
                name="countryOfBirth"
                data-cy="countryOfBirth"
                label="Country Of Birth"
                type="select"
              >
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Applicant Addresse"
                id="applicant-info-applicantAddresse"
                data-cy="applicantAddresse"
                type="select"
                multiple
                name="applicantAddresses"
              >
                <option value="" key="0" />
                {applicantAddresses
                  ? applicantAddresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Applicant Phone"
                id="applicant-info-applicantPhone"
                data-cy="applicantPhone"
                type="select"
                multiple
                name="applicantPhones"
              >
                <option value="" key="0" />
                {applicantPhones
                  ? applicantPhones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Applicant Docs"
                id="applicant-info-applicantDocs"
                data-cy="applicantDocs"
                type="select"
                multiple
                name="applicantDocs"
              >
                <option value="" key="0" />
                {applicantDocs
                  ? applicantDocs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ApplicantInfoUpdate;
