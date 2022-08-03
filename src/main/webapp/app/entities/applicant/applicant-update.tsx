import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { getEntities as getApplicantLevels } from 'app/entities/applicant-level/applicant-level.reducer';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities as getApplicantInfos } from 'app/entities/applicant-info/applicant-info.reducer';
import { IIpInfo } from 'app/shared/model/ip-info.model';
import { getEntities as getIpInfos } from 'app/entities/ip-info/ip-info.reducer';
import { IUserAgentInfo } from 'app/shared/model/user-agent-info.model';
import { getEntities as getUserAgentInfos } from 'app/entities/user-agent-info/user-agent-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant.reducer';
import { IApplicant } from 'app/shared/model/applicant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Platform } from 'app/shared/model/enumerations/platform.model';

export const ApplicantUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicantLevels = useAppSelector(state => state.applicantLevel.entities);
  const applicantInfos = useAppSelector(state => state.applicantInfo.entities);
  const ipInfos = useAppSelector(state => state.ipInfo.entities);
  const userAgentInfos = useAppSelector(state => state.userAgentInfo.entities);
  const applicantEntity = useAppSelector(state => state.applicant.entity);
  const loading = useAppSelector(state => state.applicant.loading);
  const updating = useAppSelector(state => state.applicant.updating);
  const updateSuccess = useAppSelector(state => state.applicant.updateSuccess);
  const platformValues = Object.keys(Platform);
  const handleClose = () => {
    props.history.push('/applicant' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getApplicantLevels({}));
    dispatch(getApplicantInfos({}));
    dispatch(getIpInfos({}));
    dispatch(getUserAgentInfos({}));
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
      ...applicantEntity,
      ...values,
      applicantLevel: applicantLevels.find(it => it.id.toString() === values.applicantLevel.toString()),
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
          platform: 'API',
          ...applicantEntity,
          createdAt: convertDateTimeFromServer(applicantEntity.createdAt),
          modifiedAt: convertDateTimeFromServer(applicantEntity.modifiedAt),
          applicantLevel: applicantEntity?.applicantLevel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicant.home.createOrEditLabel" data-cy="ApplicantCreateUpdateHeading">
            Create or edit a Applicant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="applicant-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Created At"
                id="applicant-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="applicant-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Modified At"
                id="applicant-modifiedAt"
                name="modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Platform" id="applicant-platform" name="platform" data-cy="platform" type="select">
                {platformValues.map(platform => (
                  <option value={platform} key={platform}>
                    {platform}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="applicant-applicantLevel"
                name="applicantLevel"
                data-cy="applicantLevel"
                label="Applicant Level"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant" replace color="info">
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

export default ApplicantUpdate;
