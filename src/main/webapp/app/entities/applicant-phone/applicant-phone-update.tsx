import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities as getApplicantInfos } from 'app/entities/applicant-info/applicant-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant-phone.reducer';
import { IApplicantPhone } from 'app/shared/model/applicant-phone.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantPhoneUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicantInfos = useAppSelector(state => state.applicantInfo.entities);
  const applicantPhoneEntity = useAppSelector(state => state.applicantPhone.entity);
  const loading = useAppSelector(state => state.applicantPhone.loading);
  const updating = useAppSelector(state => state.applicantPhone.updating);
  const updateSuccess = useAppSelector(state => state.applicantPhone.updateSuccess);
  const handleClose = () => {
    props.history.push('/applicant-phone' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplicantInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...applicantPhoneEntity,
      ...values,
      applicantInfo: applicantInfos.find(it => it.id.toString() === values.applicantInfo.toString()),
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
          ...applicantPhoneEntity,
          applicantInfo: applicantPhoneEntity?.applicantInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicantPhone.home.createOrEditLabel" data-cy="ApplicantPhoneCreateUpdateHeading">
            Create or edit a ApplicantPhone
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
                <ValidatedField name="id" required readOnly id="applicant-phone-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Country" id="applicant-phone-country" name="country" data-cy="country" type="text" />
              <ValidatedField label="Number" id="applicant-phone-number" name="number" data-cy="number" type="text" />
              <ValidatedField label="Enabled" id="applicant-phone-enabled" name="enabled" data-cy="enabled" check type="checkbox" />
              <ValidatedField
                id="applicant-phone-applicantInfo"
                name="applicantInfo"
                data-cy="applicantInfo"
                label="Applicant Info"
                type="select"
              >
                <option value="" key="0" />
                {applicantInfos
                  ? applicantInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant-phone" replace color="info">
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

export default ApplicantPhoneUpdate;
