package gov.usps.cmrt.batch.common;

public interface BatchManager {

	// if position that was previously assigned position is vacant for 14 days,
	// create notification
	public abstract void vacantNotificationsBatch();

	//Vacate any personnel assignments who's assignment end date is <= today
	//If no successor is present, generate a Vacant Notification 
	//If successor is present, generate a New Personnel Assignment Notification
	public abstract void vacateEndedPersonnelAssignments();

	//Notification generated 10 days prior to the end date being reached.
	public abstract void endDateApproachingBatch();

	public abstract void exceptionsRequestBatch();

	public abstract int getReturnCode();

}