import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStep } from 'app/shared/model/step.model';
import { getEntities as getSteps } from 'app/entities/step/step.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant-level.reducer';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantLevelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const steps = useAppSelector(state => state.step.entities);
  const applicantLevelEntity = useAppSelector(state => state.applicantLevel.entity);
  const loading = useAppSelector(state => state.applicantLevel.loading);
  const updating = useAppSelector(state => state.applicantLevel.updating);
  const updateSuccess = useAppSelector(state => state.applicantLevel.updateSuccess);
  const handleClose = () => {
    props.history.push('/applicant-level' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSteps({}));
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
      ...applicantLevelEntity,
      ...values,
      steps: mapIdList(values.steps),
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
          ...applicantLevelEntity,
          createdAt: convertDateTimeFromServer(applicantLevelEntity.createdAt),
          modifiedAt: convertDateTimeFromServer(applicantLevelEntity.modifiedAt),
          steps: applicantLevelEntity?.steps?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicantLevel.home.createOrEditLabel" data-cy="ApplicantLevelCreateUpdateHeading">
            Create or edit a ApplicantLevel
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
                <ValidatedField name="id" required readOnly id="applicant-level-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Code" id="applicant-level-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Level Name" id="applicant-level-levelName" name="levelName" data-cy="levelName" type="text" />
              <ValidatedField label="Description" id="applicant-level-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Url" id="applicant-level-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label="Created At"
                id="applicant-level-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="applicant-level-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Modified At"
                id="applicant-level-modifiedAt"
                name="modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Step" id="applicant-level-step" data-cy="step" type="select" multiple name="steps">
                <option value="" key="0" />
                {steps
                  ? steps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant-level" replace color="info">
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

export default ApplicantLevelUpdate;
