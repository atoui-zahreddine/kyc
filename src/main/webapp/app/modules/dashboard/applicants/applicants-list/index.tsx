import React, { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useHistory } from 'react-router-dom';
import { DetailsListLayoutMode, IColumn, PrimaryButton, SelectionMode, ShimmeredDetailsList, Stack, Text } from '@fluentui/react';

import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import Pagination from 'app/modules/dashboard/applicant-level/applicant-level-list/components/Pagination';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities } from 'app/entities/applicant-info/applicant-info.reducer';
import Controls from 'app/modules/dashboard/applicant-level/applicant-level-list/components/controls';

const columns: IColumn[] = [
  {
    key: 'id',
    name: 'Id',
    fieldName: 'id',
    minWidth: 70,
    maxWidth: 130,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantInfo) {
      return <span>{item.id}</span>;
    },
  },
  {
    key: 'firstName',
    name: 'First Name',
    fieldName: 'firstName',
    minWidth: 150,
    maxWidth: 250,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantInfo) {
      return <span>{item.firstName}</span>;
    },
    isPadded: true,
  },
  {
    key: 'firstName',
    name: 'Last Name',
    fieldName: 'firstName',
    minWidth: 70,
    maxWidth: 130,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantInfo) {
      return <span>{item.lastName}</span>;
    },
  },
  {
    key: 'dateOfBirth',
    name: 'Date Of Birth',
    fieldName: 'dateOfBirth',
    minWidth: 70,
    isResizable: true,
    isCollapsible: true,
    data: 'string',
    onRender(item: IApplicantInfo) {
      return <span>{item.dateOfBirth}</span>;
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

const ApplicantsList = () => {
  const dispatch = useAppDispatch();
  const router = useHistory();

  const [paginationState, setPaginationState] = useState({
    activePage: 0,
    itemsPerPage: ITEMS_PER_PAGE,
    sort: 'id,desc',
  });

  const applicantLevelList = useAppSelector(state => state.applicantInfo.entities);
  const loading = useAppSelector(state => state.applicantInfo.loading);
  const totalItems = useAppSelector(state => state.applicantInfo.totalItems);

  const goToNewLevelPage = () => {
    router.push(`${router.location.pathname}/new`);
  };

  const onPageChange = ({ selected }: { selected: number }) => {
    setPaginationState(p => ({ ...p, activePage: selected }));
  };

  useEffect(() => {
    dispatch(getEntities({ page: paginationState.activePage, size: paginationState.itemsPerPage, sort: paginationState.sort }));
  }, [paginationState.activePage]);

  return (
    <Stack tokens={{ childrenGap: 16 }}>
      <Text variant={'large'} nowrap block>
        Applicants
      </Text>
      <Stack styles={{ root: { alignItems: 'flex-end' } }}>
        <PrimaryButton onClick={goToNewLevelPage} styles={{ root: { color: 'white' } }}>
          New Applicant
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
      <Pagination onPageChange={onPageChange} totalItems={totalItems} />
    </Stack>
  );
};

export default ApplicantsList;
