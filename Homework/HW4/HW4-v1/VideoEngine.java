
package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 *
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system.  If the video does not already exist in the system,
     * then the video is added, and proper operations may be done to it.  If the video 
     * is already in the system, then an IllegalArgumentException is thrown.  If the video
     * is null, a NullPointerException is thrown.
     *
     * @param video the video to be added to the system
     * @throws IllegalArgumentException if the video already exists in the system
     * @throws NullPointerException if the video is null
     */
    public void addVideo(Video video);

    /**
     * Removes an existing video from the system.  If the video is in the system, the video
     * will be removed from the system, and made as if it never existed.  If it does not exist in the system, 
     * an IllegalArgumentException is thrown. A NullPointerException will be thrown if the method input is null.
     * 
     * @param video the video to be removed from the system
     * @throws IllegalArgumentException if the video does not exist in the system
     * @throws NullPointerException if the method input 'video' is null
     */
    public void removeVideo(Video video);

    /**
     * Adds an existing television episode to an existing television series.  If the episode and series
     * exist, the episode is added to the given series.  If the episode is null, or the series is null, 
     * a NullPointerException is thrown. If the series or episode do not exist in the system, an
     * IllegalArgumentException is thrown with a message specifying which does not exist.
     *
     *
     * @param episode the episode to be added to the series
     * @param series the series to add the episode into
     * @throws IllegalArgumentException if the episode or series do not exist
     * @throws NullPointerException if either fields are null
     */
    void addToSeries(TvEpisode episode, TvSeries series);

    /**
     * Removes a television episode from a television series.  If theEpisode exists in theSeries, then
     * theEpisode will be removed from theSeries.  If theEpisode was never assigned to theSeries, or if
     * it had been removed already, an IllegalArgumentException will be thrown.  If either theEpisode or
     * theSeries inputs are null, a NullPointerException will be thrown.  If theSeries or theEpisode do 
     * not exist in the system, an InvalidParameterException will be thrown.
     *
     * @param theEpisode episode to remove from the given series
     * @param theSeries series from which to remove the episode
     * @throws IllegalArgumentException if theEpisode does not exist in theSeries
     * @throws java.security.InvalidParameterException if either theEpisode or theSeries do not exist
     * in the system
     * @throws NullPointerException if either fields are null
     */
    public void removeFromSeries(TvEpisode theEpisode, TvSeries theSeries);

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5.  If the video already has
     * a rating from the user, an IllegalArgumentException will be thrown.  If the user needs to update
     * the rating, the existing rating should first be cleared using clearRating.
     * If the rating is less than 1 or greater than 5, an InvalidParameterException will be thrown.
     * If the video or the user
     * do not exist in the system, an InvalidParameterException will be thrown.  If either entry fields
     * are null, a NullPointerException will be thrown.
     *
     * @param theUser user to which the rating should be added
     * @param theVideo video to which the rating should be applied
     * @param rating the rating to apply to the video
     * @throws IllegalArgumentException if the user already has a rating on the video
     * @throws java.security.InvalidParameterException if either the user or the video do not exist in the system
     * @throws NullPointerException if either fields are null
     */
    public void rateVideo(User theUser, Video theVideo, int rating);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5.  It will only return a prediction for a user if the video has not yet 
     * been rated by the user. 
     * If the video had been rated by the user previously, then an IllegalArgumentException will be thrown.  
     * If either the user or video fields are null, a NullPointerException will be thrown.  
     * If the video or user does not exist in the system, an InvalidParameterException will be thrown.
     * 
     * @param user the user who is applying the rating
     * @param video the video to add the rating to
     * @throws IllegalArgumentException if the video already has a rating from the user
     * @throws java.security.InvalidParameterException if the video or user do not exist in the system
     * @throws NullPointerException if either user or video fields are null
     * @return      the predicted rating for the user at the given video
     */
    public int predictRating(User user, Video video);

    /**
     * Suggests a video for a user (e.g.,based on their predicted ratings).  The system determines a
     * suggestion for the given user and returns it.  If the user cannot receive predictions for anything
     * (hasn't rated anything or watched anything) then a random video will be suggested.  If the user 
     * field is null, a NullPointerException will be thrown.  If the user does not exist in the system,
     * an InvalidParameterException will be thrown.
     * 
     * @param user the user which the suggestion is tailored toward.
     * @throws java.security.InvalidParameterException if the user does not exist in the system
     * @throws NullPointerException if the user field is null
     * @return      the video which is suggested
     */
    public Video suggestVideo(User user);


}

