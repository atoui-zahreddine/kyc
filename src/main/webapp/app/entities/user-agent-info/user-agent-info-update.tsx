import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities as getApplicants } from 'app/entities/applicant/applicant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-agent-info.reducer';
import { IUserAgentInfo } from 'app/shared/model/user-agent-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserAgentInfoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicants = useAppSelector(state => state.applicant.entities);
  const userAgentInfoEntity = useAppSelector(state => state.userAgentInfo.entity);
  const loading = useAppSelector(state => state.userAgentInfo.loading);
  const updating = useAppSelector(state => state.userAgentInfo.updating);
  const updateSuccess = useAppSelector(state => state.userAgentInfo.updateSuccess);
  const handleClose = () => {
    props.history.push('/user-agent-info' + props.location.search);
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
      ...userAgentInfoEntity,
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
          ...userAgentInfoEntity,
          applicant: userAgentInfoEntity?.applicant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.userAgentInfo.home.createOrEditLabel" data-cy="UserAgentInfoCreateUpdateHeading">
            Create or edit a UserAgentInfo
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
                <ValidatedField name="id" required readOnly id="user-agent-info-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Ua Browser" id="user-agent-info-uaBrowser" name="uaBrowser" data-cy="uaBrowser" type="text" />
              <ValidatedField
                label="Ua Browser Version"
                id="user-agent-info-uaBrowserVersion"
                name="uaBrowserVersion"
                data-cy="uaBrowserVersion"
                type="text"
              />
              <ValidatedField
                label="Ua Device Type"
                id="user-agent-info-uaDeviceType"
                name="uaDeviceType"
                data-cy="uaDeviceType"
                type="text"
              />
              <ValidatedField label="Ua Platform" id="user-agent-info-uaPlatform" name="uaPlatform" data-cy="uaPlatform" type="text" />
              <ValidatedField id="user-agent-info-applicant" name="applicant" data-cy="applicant" label="Applicant" type="select">
                <option value="" key="0" />
                {applicants
                  ? applicants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-agent-info" replace color="info">
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

export default UserAgentInfoUpdate;
