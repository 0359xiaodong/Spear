/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.spear;

import android.content.Context;
import android.widget.ImageView.ScaleType;

import me.xiaopan.android.spear.display.ImageDisplayer;
import me.xiaopan.android.spear.display.TransitionImageDisplayer;
import me.xiaopan.android.spear.process.ImageProcessor;
import me.xiaopan.android.spear.request.DisplayRequest;
import me.xiaopan.android.spear.util.DrawableHolder;
import me.xiaopan.android.spear.util.ImageSize;

/**
 * 显示选项
 */
public class DisplayOptions extends LoadOptions {
	protected boolean enableMemoryCache = DisplayRequest.DEFAULT_ENABLE_MEMORY_CACHE;	//是否每次加载图片的时候先从内存中去找，并且加载完成后将图片缓存在内存中
    protected ImageDisplayer imageDisplayer;	// 图片显示器
    protected DrawableHolder loadingDrawableHolder;	//当正在加载时显示的图片
    protected DrawableHolder loadFailDrawableHolder;	//当加载失败时显示的图片
    protected boolean resizeByImageViewLayoutSize;
    protected boolean resizeByImageViewLayoutSizeFromDisplayer;

    public DisplayOptions(Context context) {
        super(context);
    }

    /**
     * 关闭内存缓存
     * @return DisplayOptions
     */
    public DisplayOptions disableMemoryCache() {
        this.enableMemoryCache = false;
        return this;
    }

    /**
     * 设置图片显示器，在加载完成后会调用此显示器来显示图片
     * @param displayer 图片显示器
     * @return DisplayOptions
     */
    public DisplayOptions displayer(ImageDisplayer displayer) {
        this.imageDisplayer = displayer;
        if(this.imageDisplayer != null && this.imageDisplayer instanceof TransitionImageDisplayer){
            if(!this.resizeByImageViewLayoutSize){
                this.resizeByImageViewLayoutSize = true;
                this.resizeByImageViewLayoutSizeFromDisplayer = true;
            }
        }else if(this.resizeByImageViewLayoutSizeFromDisplayer){
            this.resizeByImageViewLayoutSize = false;
            this.resizeByImageViewLayoutSizeFromDisplayer = false;
        }
        return this;
    }

    /**
     * 设置正在加载的时候显示的图片
     * @param drawableResId 正在加载的时候显示的图片
     * @return DisplayOptions
     */
    public DisplayOptions loadingDrawable(int drawableResId) {
        if(loadingDrawableHolder == null){
            loadingDrawableHolder = new DrawableHolder();
        }
        loadingDrawableHolder.setResId(drawableResId);
        return this;
    }

    /**
     * 设置正在加载的时候显示的图片
     * @param drawableResId 正在加载的时候显示的图片
     * @param isProcess 是否使用ImageProcessor对当前图片进行处理
     * @return DisplayOptions
     */
    public DisplayOptions loadingDrawable(int drawableResId, boolean isProcess) {
        if(loadingDrawableHolder == null){
            loadingDrawableHolder = new DrawableHolder();
        }
        loadingDrawableHolder.setResId(drawableResId);
        loadingDrawableHolder.setProcess(isProcess);
        return this;
    }

    /**
     * 设置当加载失败的时候显示的图片
     * @param drawableResId 当加载失败的时候显示的图片
     * @return DisplayOptions
     */
    public DisplayOptions loadFailDrawable(int drawableResId) {
        if(loadFailDrawableHolder == null){
            loadFailDrawableHolder = new DrawableHolder();
        }
        loadFailDrawableHolder.setResId(drawableResId);
        return this;
    }

    /**
     * 设置当加载失败的时候显示的图片
     * @param drawableResId 当加载失败的时候显示的图片
     * @param isProcess 是否使用ImageProcessor对当前图片进行处理
     * @return DisplayOptions
     */
    public DisplayOptions loadFailDrawable(int drawableResId, boolean isProcess) {
        if(loadFailDrawableHolder == null){
            loadFailDrawableHolder = new DrawableHolder();
        }
        loadFailDrawableHolder.setResId(drawableResId);
        loadFailDrawableHolder.setProcess(isProcess);
        return this;
    }

    @Override
    public DisplayOptions processor(ImageProcessor processor) {
        super.processor(processor);
        if(loadingDrawableHolder != null && loadingDrawableHolder.isProcess()){
            loadingDrawableHolder.reset();
        }
        if(loadFailDrawableHolder != null && loadFailDrawableHolder.isProcess()){
            loadFailDrawableHolder.reset();
        }
        return this;
    }

    @Override
    public DisplayOptions maxsize(ImageSize maxsize){
        super.maxsize(maxsize);
        return this;
    }

    @Override
    public DisplayOptions maxsize(int width, int height) {
        super.maxsize(width, height);
        return this;
    }

    @Override
    public DisplayOptions resize(ImageSize resize){
        super.resize(resize);
        this.resizeByImageViewLayoutSize = false;
        this.resizeByImageViewLayoutSizeFromDisplayer = false;
        return this;
    }

    @Override
    public DisplayOptions resize(int width, int height) {
        super.resize(width, height);
        this.resizeByImageViewLayoutSize = false;
        this.resizeByImageViewLayoutSizeFromDisplayer = false;
        return this;
    }

    /**
     * 根据ImageView的Layout Size裁剪图片
     */
    public void resizeByImageViewLayoutSize() {
        this.resizeByImageViewLayoutSize = true;
        this.resizeByImageViewLayoutSizeFromDisplayer = false;
    }

    @Override
    public DisplayOptions scaleType(ScaleType scaleType) {
        super.scaleType(scaleType);
        return this;
    }

    @Override
    public DisplayOptions disableDiskCache() {
        super.disableDiskCache();
        return this;
    }

    public boolean isResizeByImageViewLayoutSize() {
        return resizeByImageViewLayoutSize;
    }

    /**
     * 是否开启了内存缓存
     * @return 是否开启了内存缓存
     */
    public boolean isEnableMemoryCache() {
        return enableMemoryCache;
    }

    /**
     * 获取图片显示器
     * @return 图片显示器
     */
    public ImageDisplayer getImageDisplayer() {
        return imageDisplayer;
    }

    /**
     * 获取加载中时显示的图片
     * @return 加载中时显示的图片
     */
    public DrawableHolder getLoadingDrawableHolder() {
        return loadingDrawableHolder;
    }

    /**
     * 获取加载失败时显示的图片
     * @return 加载失败时显示的图片
     */
    public DrawableHolder getLoadFailDrawableHolder() {
        return loadFailDrawableHolder;
    }

    public boolean isResizeByImageViewLayoutSizeFromDisplayer() {
        return resizeByImageViewLayoutSizeFromDisplayer;
    }
}
