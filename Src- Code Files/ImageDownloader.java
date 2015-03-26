/*
 * This class file downloads an image from a given url in an AsyncTask(background). 
 * This can be used to download images of the diagrams in background from github. 
 * Each file in github has a raw link which can be used as an url to download images of the diagrams.
 * An example of a raw link is https://raw.githubusercontent.com/NagabhushanS/BundledImages/master/Screenshot%20from%202015-03-10%2016%3A46%3A58.png
 */

package com.example.illustrativeproject;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;


// AsyncTask class to download images of the diagrams from github.
class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... param){
		return downloadBitmap(param[0]);
	}

	@Override
	protected void onPreExecute() {
		// Log the status
		Log.i("Async-Example", "onPreExecute Called");

	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// Log the status
		Log.i("Async-Example", "onPostExecute Called");

	}

	private Bitmap downloadBitmap(String url) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttpGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving diagram from " + url);
				return null;

			}

			// forming the HttpEntity
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that android
					// understands
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);

					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// We Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("ImageDownloader", "Something went wrong while"
					+ " retrieving bitmap from " + url + e.toString());
		}

		return null;
	}

}
