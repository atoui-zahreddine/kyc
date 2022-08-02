import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { getEntities as getApplicantLevels } from 'app/entities/applicant-level/applicant-level.reducer';
import { getEntity, updateEntity, createEntity, reset } from './step.reducer';
import { IStep } from 'app/shared/model/step.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StepUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicantLevels = useAppSelector(state => state.applicantLevel.entities);
  const stepEntity = useAppSelector(state => state.step.entity);
  const loading = useAppSelector(state => state.step.loading);
  const updating = useAppSelector(state => state.step.updating);
  const updateSuccess = useAppSelector(state => state.step.updateSuccess);
  const handleClose = () => {
    props.history.push('/step' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplicantLevels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.modifiedAt = convertDateTimeToServer(values.modifiedAt);

    const entity = {
      ...stepEntity,
      ...values,
      applicantLevels: mapIdList(values.applicantLevels),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
          modifiedAt: displayDefaultDateTime(),
        }
      : {
          ...stepEntity,
          createdAt: convertDateTimeFromServer(stepEntity.createdAt),
          modifiedAt: convertDateTimeFromServer(stepEntity.modifiedAt),
          applicantLevels: stepEntity?.applicantLevels?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.step.home.createOrEditLabel" data-cy="StepCreateUpdateHeading">
            Create or edit a Step
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="step-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="step-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Name" id="step-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Description" id="step-description" name="description" data-cy="description" type="text" />
              <ValidatedField
                label="Created At"
                id="step-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="step-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Modified At"
                id="step-modifiedAt"
                name="modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Applicant Level"
                id="step-applicantLevel"
                data-cy="applicantLevel"
                type="select"
                multiple
                name="applicantLevels"
              >
                <option value="" key="0" />
                {applicantLevels
                  ? applicantLevels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/step" replace color="info">
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

export default StepUpdate;
