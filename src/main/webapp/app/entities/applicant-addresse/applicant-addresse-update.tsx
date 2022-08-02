import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities as getApplicantInfos } from 'app/entities/applicant-info/applicant-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant-addresse.reducer';
import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantAddresseUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicantInfos = useAppSelector(state => state.applicantInfo.entities);
  const applicantAddresseEntity = useAppSelector(state => state.applicantAddresse.entity);
  const loading = useAppSelector(state => state.applicantAddresse.loading);
  const updating = useAppSelector(state => state.applicantAddresse.updating);
  const updateSuccess = useAppSelector(state => state.applicantAddresse.updateSuccess);
  const handleClose = () => {
    props.history.push('/applicant-addresse' + props.location.search);
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
      ...applicantAddresseEntity,
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
          ...applicantAddresseEntity,
          applicantInfo: applicantAddresseEntity?.applicantInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicantAddresse.home.createOrEditLabel" data-cy="ApplicantAddresseCreateUpdateHeading">
            Create or edit a ApplicantAddresse
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
                <ValidatedField name="id" required readOnly id="applicant-addresse-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Post Code" id="applicant-addresse-postCode" name="postCode" data-cy="postCode" type="text" />
              <ValidatedField label="State" id="applicant-addresse-state" name="state" data-cy="state" type="text" />
              <ValidatedField label="Street" id="applicant-addresse-street" name="street" data-cy="street" type="text" />
              <ValidatedField label="Sub Street" id="applicant-addresse-subStreet" name="subStreet" data-cy="subStreet" type="text" />
              <ValidatedField label="Town" id="applicant-addresse-town" name="town" data-cy="town" type="text" />
              <ValidatedField label="Enabled" id="applicant-addresse-enabled" name="enabled" data-cy="enabled" check type="checkbox" />
              <ValidatedField
                id="applicant-addresse-applicantInfo"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant-addresse" replace color="info">
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

export default ApplicantAddresseUpdate;
