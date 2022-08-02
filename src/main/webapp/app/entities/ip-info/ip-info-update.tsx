import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities as getApplicants } from 'app/entities/applicant/applicant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ip-info.reducer';
import { IIpInfo } from 'app/shared/model/ip-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IpInfoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicants = useAppSelector(state => state.applicant.entities);
  const ipInfoEntity = useAppSelector(state => state.ipInfo.entity);
  const loading = useAppSelector(state => state.ipInfo.loading);
  const updating = useAppSelector(state => state.ipInfo.updating);
  const updateSuccess = useAppSelector(state => state.ipInfo.updateSuccess);
  const handleClose = () => {
    props.history.push('/ip-info' + props.location.search);
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
      ...ipInfoEntity,
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
          ...ipInfoEntity,
          applicant: ipInfoEntity?.applicant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.ipInfo.home.createOrEditLabel" data-cy="IpInfoCreateUpdateHeading">
            Create or edit a IpInfo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ip-info-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Asn" id="ip-info-asn" name="asn" data-cy="asn" type="text" />
              <ValidatedField label="Asn Org" id="ip-info-asnOrg" name="asnOrg" data-cy="asnOrg" type="text" />
              <ValidatedField label="Country Code 2" id="ip-info-countryCode2" name="countryCode2" data-cy="countryCode2" type="text" />
              <ValidatedField label="Country Code 3" id="ip-info-countryCode3" name="countryCode3" data-cy="countryCode3" type="text" />
              <ValidatedField label="Ip" id="ip-info-ip" name="ip" data-cy="ip" type="text" />
              <ValidatedField label="Lat" id="ip-info-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label="Lon" id="ip-info-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField id="ip-info-applicant" name="applicant" data-cy="applicant" label="Applicant" type="select">
                <option value="" key="0" />
                {applicants
                  ? applicants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ip-info" replace color="info">
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

export default IpInfoUpdate;
