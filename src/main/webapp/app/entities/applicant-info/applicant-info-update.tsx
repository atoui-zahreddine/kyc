import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities as getApplicants } from 'app/entities/applicant/applicant.reducer';
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
      applicant: applicants.find(it => it.id.toString() === values.applicant.toString()),
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
