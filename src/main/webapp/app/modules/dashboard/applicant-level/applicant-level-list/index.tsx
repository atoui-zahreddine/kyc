import './applicant-level-list.scss';

import React, { useEffect, useMemo } from 'react';
import { RouteComponentProps, useHistory, Link, useLocation } from 'react-router-dom';
import {
  ShimmeredDetailsList,
  DetailsListLayoutMode,
  IColumn,
  Text,
  Stack,
  IconButton,
  initializeIcons,
  SelectionMode,
  Callout,
  Icon,
} from '@fluentui/react';
import { useBoolean, useId } from '@fluentui/react-hooks';

import { IDocument } from 'app/shared/model/applicant-level-list-column';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/applicant-level/applicant-level.reducer';

initializeIcons();

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
    onRender(item: IDocument) {
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
    onRender(item: IDocument) {
      return <span>{item.steps}</span>;
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
    onRender(item: IDocument) {
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
    onRender(item: IDocument) {
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
    onRender() {
      const [isCalloutVisible, { toggle: toggleIsCalloutVisible }] = useBoolean(false);
      const location = useLocation();
      const buttonId = useId('callout-button');

      return (
        <>
          <IconButton id={buttonId} iconProps={{ iconName: 'more' }} aria-label="More" onClick={toggleIsCalloutVisible} />
          {isCalloutVisible && (
            <Callout role="dialog" target={`#${buttonId}`} onDismiss={toggleIsCalloutVisible} setInitialFocus>
              <Stack tokens={{ childrenGap: 16, padding: 8 }}>
                <Icon iconName="ComplianceAudit" aria-label="Details" />
                <Link to={`${location.pathname}/details`}>Details</Link>
                <Link to={``}>delete</Link>
              </Stack>
            </Callout>
          )}
        </>
      );
    },
  },
];

export const ApplicantLevelList = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();
  const applicantLevelList = useAppSelector(state => state.applicantLevel.entities);
  const loading = useAppSelector(state => state.applicantLevel.loading);
  const applicantLevels = useMemo(() => {
    return applicantLevelList.map<IDocument>(level => ({
      levelName: level.levelName,
      createdAt: level.createdAt,
      createdBy: level.createdBy,
      steps: level.steps?.length,
    }));
  }, [applicantLevelList]);

  useEffect(() => {
    dispatch(getEntities({ page: 0, size: 5 }));
  }, []);

  return (
    <Stack tokens={{ childrenGap: 16 }}>
      <Text variant={'large'} nowrap block>
        Applicant Levels
      </Text>
      <ShimmeredDetailsList
        shimmerLines={5}
        enableShimmer={loading}
        items={applicantLevels}
        columns={columns}
        selectionMode={SelectionMode.none}
        layoutMode={DetailsListLayoutMode.justified}
        isHeaderVisible={true}
      />
    </Stack>
  );
};

export default ApplicantLevelList;
