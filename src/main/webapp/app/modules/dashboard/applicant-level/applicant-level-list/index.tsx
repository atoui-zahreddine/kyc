import './applicant-level-list.scss';

import React, { useEffect } from 'react';
import { Link, RouteComponentProps, useHistory } from 'react-router-dom';
import {
  DetailsListLayoutMode,
  IColumn,
  SelectionMode,
  PrimaryButton,
  ShimmeredDetailsList,
  Stack,
  Text,
  mergeStyles,
} from '@fluentui/react';

import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/applicant-level/applicant-level.reducer';
import Controls from './components/controls';

const columns: IColumn[] = [
  {
    key: 'levelName',
    name: 'Level Name',
    fieldName: 'levelName',
    minWidth: 150,
    maxWidth: 250,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantLevel) {
      return <span>{item.levelName}</span>;
    },
    isPadded: true,
  },
  {
    key: 'steps',
    name: 'steps',
    fieldName: 'steps',
    minWidth: 70,
    maxWidth: 130,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantLevel) {
      return <span>{item.steps?.length || 0}</span>;
    },
  },
  {
    key: 'createdAt',
    name: 'Created',
    fieldName: 'createdAt',
    minWidth: 70,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantLevel) {
      return <span>{item.createdAt}</span>;
    },
  },
  {
    key: 'createdBy',
    name: 'Created By',
    fieldName: 'createdBy',
    minWidth: 70,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantLevel) {
      return <span>{item.createdBy}</span>;
    },
  },
  {
    key: 'menu',
    name: '',
    fieldName: '',
    minWidth: 70,
    isResizable: true,
    isCollapsible: true,
    data: '',
    className: 'more-icon-container',
    onRender: Controls,
  },
];

const styles = mergeStyles({
  color: 'white',
  textDecoration: 'none',
});

export const ApplicantLevelList = () => {
  const dispatch = useAppDispatch();
  const applicantLevelList = useAppSelector(state => state.applicantLevel.entities);
  const loading = useAppSelector(state => state.applicantLevel.loading);
  const router = useHistory();

  const goToNewLevelPage = () => {
    router.push(`${router.location.pathname}/new`);
  };

  useEffect(() => {
    dispatch(getEntities({ page: 0, size: 5 }));
  }, []);

  return (
    <Stack tokens={{ childrenGap: 16 }}>
      <Text variant={'large'} nowrap block>
        Applicant Levels
      </Text>
      <Stack styles={{ root: { alignItems: 'flex-end' } }}>
        <PrimaryButton onClick={goToNewLevelPage} styles={{ root: { color: 'white' } }}>
          New Applicant Level
        </PrimaryButton>
      </Stack>
      <ShimmeredDetailsList
        shimmerLines={5}
        enableShimmer={loading}
        items={[...applicantLevelList]}
        columns={columns}
        selectionMode={SelectionMode.none}
        layoutMode={DetailsListLayoutMode.justified}
        isHeaderVisible={true}
      />
    </Stack>
  );
};

export default ApplicantLevelList;
